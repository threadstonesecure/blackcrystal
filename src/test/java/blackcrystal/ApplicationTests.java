package blackcrystal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@TestPropertySource(properties = { "spring.jmx.enabled:true",
		"spring.datasource.jmx-enabled:true" })
@ActiveProfiles("scratch")
public class ApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testHealth() throws Exception {
		this.mvc.perform(get("/health")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("UP")));
	}



	@Test
	public void testJmx() throws Exception {
		assertEquals(1, ManagementFactory.getPlatformMBeanServer()
				.queryMBeans(new ObjectName("jpa.sample:type=ConnectionPool,*"), null)
				.size());
	}

}
