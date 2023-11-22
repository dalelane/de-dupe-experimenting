#!/bin/bash

#
# send events from the data.csv file to the input topic
#
#  for example:
#
#     send-from-csv.sh 1 8
#    will send 8 events to the input topic, with the data from rows 1-8
#
#     send-from-csv.sh 5 12
#    will send 8 events to the input topic, with the data from rows 5-12
#
#  NOTE: URL, username and password needs to be replaced to run this
#

load_csv_file() {
    CSV_FILE="${1}"
    TOPIC="${2}"
    START="${3}"
    END="${4}"

    IFS=',' read -a columns < "$CSV_FILE"

    line=1

    tail -n +2 "$CSV_FILE" | while IFS=',' read -a values
    do
        json_payload="{"

        for i in $(seq 0 $((${#columns[@]} - 2))); do
            key="${columns[$i]}"
            value="${values[$i]}"

            if [[ $value =~ ^[0-9]+([.][0-9]+)?$ ]]
            then
                json_payload+="\"value\":$value"
            else
                json_payload+="\"entity\":\"$value\""
            fi

            if [ $i -lt $((${#columns[@]} - 2)) ]
            then
                json_payload+=","
            fi
        done

        json_payload+="}"

        if [[ $line -ge $START ]] && [[ $line -le  $END ]]; then
            echo "$json_payload"
            curl \
                --silent \
                -X POST \
                -H "Content-Type: application/json" \
                -k \
                --data "$json_payload" \
                -u event-streams-username:event-streams-password \
                https://event-streams-rest-producer-api/topics/$TOPIC/records > /dev/null
        fi

        line=$((line+1))
    done
}


startrow=$1
endrow=$2

echo "generating events using rows $startrow to $endrow"

load_csv_file "data.csv" "DEDUPE.INPUT" $startrow $endrow
