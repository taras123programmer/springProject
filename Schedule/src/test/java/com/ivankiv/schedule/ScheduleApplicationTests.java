package com.ivankiv.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class 	ScheduleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	int groupId = 1;
	String date = "2024-11-24";


	@Test
	void testGetSchedule() throws  Exception {
		String response = """		
			{
			    "date": "2024-11-24",
			    "group_id": 1,
			    "lessons": {
			      "1": {
			        "object": "java",
			        "teacher": "Козленко",
			        "corps": "Гуманітарний корпус",
			        "address": "Шевченка 57",
			        "cabinet": 111,
			        "type": "Практична",
			        "begin": "09:00:00"
			      },
			      "2": {
			        "object": "Python",
			        "teacher": "Іщеряков",
			        "corps": "Центральний корпус",
			        "address": "Шевченка 57",
			        "cabinet": 333,
			        "type": "Лекція",
			        "begin": "10:30:00"
			      },
			      "3": {
			        "object": "Бази даних",
			        "teacher": "Лазарович",
			        "corps": "Центральний корпус",
			        "address": "Шевченка 57",
			        "cabinet": 222,
			        "type": "Практична",
			        "begin": "12:00:00"
			      }
			    }
			  }
		""";

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/schedule?group_id=%d&date=%s", groupId, date)))
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}

	@Test
	void testDeleteSchedule() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/schedule?group_id=%d&date=%s",groupId, date)))
				.andExpect((status().isNoContent()));

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/schedule?group_id=%d&date=%s", groupId, date)))
				.andExpect(status().isNotFound());

	}

	@Test
	void testPostSchedule() throws Exception{

		String request = """
				{
				  "date": "2024-11-24",
				  "group_id": 1,
				  "lessons": {
				    "1": {
				     "teacherId": 1,
					 "object": "java",
					 "corpsId": 2,
					 "cabinet": 111,
					 "type": "Практична"
				   },
				   "2": {
					 "teacherId": 3,
					 "object": "Python",
					 "corpsId": 1,
					 "cabinet": 333,
					 "type": "Лекція"
				   },
				   "3": {
					 "teacherId": 2,
					 "object": "Бази даних",
					 "corpsId": 1,
					 "cabinet": 222,
					 "type": "Практична"
				   }
				 }
				}
				""";

		ResultActions res = mockMvc.perform( MockMvcRequestBuilders.post("/api/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request) );

		if(res.andReturn().getResponse().getStatus() == 400){
			testDeleteSchedule();
			res = mockMvc.perform( MockMvcRequestBuilders.post("/api/schedule")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request) );
		}

		res.andExpect(status().isCreated());

		testGetSchedule();

	}

	@Test
	void testScheduleAPI() throws Exception{

		testGetSchedule();
		testDeleteSchedule();
		testPostSchedule();

	}

}
