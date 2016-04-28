package com.sfeir.bank;

import com.sfeir.bank.BankApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BankApplication.class)
@WebAppConfiguration
public class BankApplicationTests {

	@Test
	public void contextLoads() {
	}

}
