package com.ibm.eventstreams;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestData {

	public static final Map<String, Object[][]> get() throws ParseException {
		Map<String, Object[][]> TEST_DATA = new LinkedHashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		TEST_DATA.put("Monday 6th November       (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"AAAAAAAA\", \"value\": 100, \"timestamp\": \"2023-11-06 10:00:00.000\" }", df.parse("2023-11-06 10:00:00.000") },
				new Object[] { "{ \"id\": \"BBBBBBBB\", \"value\": 200, \"timestamp\": \"2023-11-06 11:00:00.000\" }", df.parse("2023-11-06 11:00:00.000") },
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 300, \"timestamp\": \"2023-11-06 12:00:00.000\" }", df.parse("2023-11-06 12:00:00.000") },
				new Object[] { "{ \"id\": \"DDDDDDDD\", \"value\": 400, \"timestamp\": \"2023-11-06 13:00:00.000\" }", df.parse("2023-11-06 13:00:00.000") }
			});
		TEST_DATA.put("Tuesday 7th November      (deletions: BBBBBBBB)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"AAAAAAAA\", \"value\": 150, \"timestamp\": \"2023-11-07 11:00:00.000\" }", df.parse("2023-11-07 11:00:00.000") },
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 360, \"timestamp\": \"2023-11-07 12:00:00.000\" }", df.parse("2023-11-07 12:00:00.000") },
				new Object[] { "{ \"id\": \"DDDDDDDD\", \"value\": 480, \"timestamp\": \"2023-11-07 13:00:00.000\" }", df.parse("2023-11-07 13:00:00.000") },
				new Object[] { "{ \"id\": \"EEEEEEEE\", \"value\": 400, \"timestamp\": \"2023-11-07 14:00:00.000\" }", df.parse("2023-11-07 14:00:00.000") }
			});
		TEST_DATA.put("Wednesday 8th November    (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"AAAAAAAA\", \"value\": 170, \"timestamp\": \"2023-11-08 10:00:00.000\" }", df.parse("2023-11-08 10:00:00.000") },
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 320, \"timestamp\": \"2023-11-08 11:00:00.000\" }", df.parse("2023-11-08 11:00:00.000") },
				new Object[] { "{ \"id\": \"DDDDDDDD\", \"value\": 410, \"timestamp\": \"2023-11-08 12:00:00.000\" }", df.parse("2023-11-08 12:00:00.000") },
				new Object[] { "{ \"id\": \"EEEEEEEE\", \"value\": 475, \"timestamp\": \"2023-11-08 13:00:00.000\" }", df.parse("2023-11-08 13:00:00.000") },
				new Object[] { "{ \"id\": \"FFFFFFFF\", \"value\": 600, \"timestamp\": \"2023-11-08 14:00:00.000\" }", df.parse("2023-11-08 14:00:00.000") },
			});
		TEST_DATA.put("Thursday 9th November     (deletions: AAAAAAAA, DDDDDDDD)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 390, \"timestamp\": \"2023-11-09 09:00:00.000\" }", df.parse("2023-11-09 09:00:00.000") },
				new Object[] { "{ \"id\": \"EEEEEEEE\", \"value\": 415, \"timestamp\": \"2023-11-09 10:00:00.000\" }", df.parse("2023-11-09 10:00:00.000") },
				new Object[] { "{ \"id\": \"FFFFFFFF\", \"value\": 620, \"timestamp\": \"2023-11-09 11:00:00.000\" }", df.parse("2023-11-09 11:00:00.000") },
			});
		TEST_DATA.put("Friday 10th November      (deletions: FFFFFFFF)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 360, \"timestamp\": \"2023-11-10 10:00:00.000\" }", df.parse("2023-11-10 10:00:00.000") },
				new Object[] { "{ \"id\": \"EEEEEEEE\", \"value\": 445, \"timestamp\": \"2023-11-10 11:00:00.000\" }", df.parse("2023-11-10 11:00:00.000") }
			});
		TEST_DATA.put("Saturday 11th November    (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 350, \"timestamp\": \"2023-11-11 10:00:00.000\" }", df.parse("2023-11-11 10:00:00.000") },
				new Object[] { "{ \"id\": \"EEEEEEEE\", \"value\": 415, \"timestamp\": \"2023-11-11 11:00:00.000\" }", df.parse("2023-11-11 11:00:00.000") },
				new Object[] { "{ \"id\": \"GGGGGGGG\", \"value\": 110, \"timestamp\": \"2023-11-11 12:00:00.000\" }", df.parse("2023-11-11 12:00:00.000") },
				new Object[] { "{ \"id\": \"HHHHHHHH\", \"value\": 210, \"timestamp\": \"2023-11-11 13:00:00.000\" }", df.parse("2023-11-11 13:00:00.000") },
				new Object[] { "{ \"id\": \"JJJJJJJJ\", \"value\": 810, \"timestamp\": \"2023-11-11 14:00:00.000\" }", df.parse("2023-11-11 14:00:00.000") }
			});
		TEST_DATA.put("Sunday 12th November      (deletions: EEEEEEEE)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 350, \"timestamp\": \"2023-11-12 09:00:00.000\" }", df.parse("2023-11-12 09:00:00.000") },
				new Object[] { "{ \"id\": \"GGGGGGGG\", \"value\": 190, \"timestamp\": \"2023-11-12 10:00:00.000\" }", df.parse("2023-11-12 10:00:00.000") },
				new Object[] { "{ \"id\": \"HHHHHHHH\", \"value\": 610, \"timestamp\": \"2023-11-12 11:00:00.000\" }", df.parse("2023-11-12 11:00:00.000") },
				new Object[] { "{ \"id\": \"JJJJJJJJ\", \"value\": 510, \"timestamp\": \"2023-11-12 12:00:00.000\" }", df.parse("2023-11-12 12:00:00.000") }
			});
		TEST_DATA.put("Monday 13th November      (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"HHHHHHHH\", \"value\": 610, \"timestamp\": \"2023-11-13 10:00:00.000\" }", df.parse("2023-11-13 10:00:00.000") },
				new Object[] { "{ \"id\": \"JJJJJJJJ\", \"value\": 510, \"timestamp\": \"2023-11-13 11:00:00.000\" }", df.parse("2023-11-13 11:00:00.000") },
				new Object[] { "{ \"id\": \"CCCCCCCC\", \"value\": 350, \"timestamp\": \"2023-11-13 12:00:00.000\" }", df.parse("2023-11-13 12:00:00.000") },
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 495, \"timestamp\": \"2023-11-13 13:00:00.000\" }", df.parse("2023-11-13 13:00:00.000") },
				new Object[] { "{ \"id\": \"GGGGGGGG\", \"value\": 190, \"timestamp\": \"2023-11-13 14:00:00.000\" }", df.parse("2023-11-13 14:00:00.000") }
			});
		TEST_DATA.put("Tuesday 14th November     (deletions: CCCCCCCC)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 350, \"timestamp\": \"2023-11-14 09:00:00.000\" }", df.parse("2023-11-14 09:00:00.000") },
				new Object[] { "{ \"id\": \"HHHHHHHH\", \"value\": 630, \"timestamp\": \"2023-11-14 10:00:00.000\" }", df.parse("2023-11-14 10:00:00.000") },
				new Object[] { "{ \"id\": \"JJJJJJJJ\", \"value\": 500, \"timestamp\": \"2023-11-14 11:00:00.000\" }", df.parse("2023-11-14 11:00:00.000") },
				new Object[] { "{ \"id\": \"GGGGGGGG\", \"value\": 170, \"timestamp\": \"2023-11-14 12:00:00.000\" }", df.parse("2023-11-14 12:00:00.000") }
			});
		TEST_DATA.put("Wednesday 15th November   (deletions: GGGGGGGG, HHHHHHHH, JJJJJJJJ)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"LLLLLLLL\", \"value\": 140, \"timestamp\": \"2023-11-15 11:00:00.000\" }", df.parse("2023-11-15 11:00:00.000") },
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 270, \"timestamp\": \"2023-11-15 12:00:00.000\" }", df.parse("2023-11-15 12:00:00.000") },
				new Object[] { "{ \"id\": \"MMMMMMMM\", \"value\": 340, \"timestamp\": \"2023-11-15 13:00:00.000\" }", df.parse("2023-11-15 13:00:00.000") },
				new Object[] { "{ \"id\": \"NNNNNNNN\", \"value\": 210, \"timestamp\": \"2023-11-15 14:00:00.000\" }", df.parse("2023-11-15 14:00:00.000") },
			});
		TEST_DATA.put("Thursday 16th November   (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"LLLLLLLL\", \"value\": 140, \"timestamp\": \"2023-11-16 13:00:00.000\" }", df.parse("2023-11-16 13:00:00.000") },
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 270, \"timestamp\": \"2023-11-16 14:00:00.000\" }", df.parse("2023-11-16 14:00:00.000") },
				new Object[] { "{ \"id\": \"MMMMMMMM\", \"value\": 340, \"timestamp\": \"2023-11-16 15:00:00.000\" }", df.parse("2023-11-16 15:00:00.000") },
				new Object[] { "{ \"id\": \"NNNNNNNN\", \"value\": 210, \"timestamp\": \"2023-11-16 16:00:00.000\" }", df.parse("2023-11-16 16:00:00.000") },
				new Object[] { "{ \"id\": \"PPPPPPPP\", \"value\": 130, \"timestamp\": \"2023-11-16 17:00:00.000\" }", df.parse("2023-11-16 17:00:00.000") },
				new Object[] { "{ \"id\": \"QQQQQQQQ\", \"value\": 670, \"timestamp\": \"2023-11-16 18:00:00.000\" }", df.parse("2023-11-16 18:00:00.000") },
			});
		TEST_DATA.put("Friday 17th November   (deletions: MMMMMMMM, QQQQQQQQ)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"LLLLLLLL\", \"value\": 140, \"timestamp\": \"2023-11-17 09:00:00.000\" }", df.parse("2023-11-16 09:00:00.000") },
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 270, \"timestamp\": \"2023-11-17 10:00:00.000\" }", df.parse("2023-11-16 10:00:00.000") },
				new Object[] { "{ \"id\": \"NNNNNNNN\", \"value\": 210, \"timestamp\": \"2023-11-17 11:00:00.000\" }", df.parse("2023-11-16 11:00:00.000") },
				new Object[] { "{ \"id\": \"PPPPPPPP\", \"value\": 130, \"timestamp\": \"2023-11-17 12:00:00.000\" }", df.parse("2023-11-16 12:00:00.000") }
			});		
		TEST_DATA.put("Saturday 18th November   (no deletions)", 
			new Object[][] { 
				new Object[] { "{ \"id\": \"LLLLLLLL\", \"value\": 140, \"timestamp\": \"2023-11-18 10:00:00.000\" }", df.parse("2023-11-18 10:00:00.000") },
				new Object[] { "{ \"id\": \"KKKKKKKK\", \"value\": 270, \"timestamp\": \"2023-11-18 11:00:00.000\" }", df.parse("2023-11-18 11:00:00.000") },
				new Object[] { "{ \"id\": \"NNNNNNNN\", \"value\": 210, \"timestamp\": \"2023-11-18 12:00:00.000\" }", df.parse("2023-11-18 12:00:00.000") },
				new Object[] { "{ \"id\": \"PPPPPPPP\", \"value\": 130, \"timestamp\": \"2023-11-18 13:00:00.000\" }", df.parse("2023-11-18 13:00:00.000") },
				new Object[] { "{ \"id\": \"RRRRRRRR\", \"value\": 110, \"timestamp\": \"2023-11-18 14:00:00.000\" }", df.parse("2023-11-18 14:00:00.000") }
			});		
		return TEST_DATA;
	}
}
