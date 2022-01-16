package com.oligas.restservice;

import com.oligas.controllers.EmbarqueDesembarqueController;
import com.oligas.controllers.FuncionarioController;
import com.oligas.repository.FuncionarioRepository;
import com.oligas.services.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class RestServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	FuncionarioController funcionarioController;
	@Autowired
	EmbarqueDesembarqueController embarqueDesembarqueController;

	@Test
	void Teste(){

		System.out.print("TESTE");

		funcionarioController.listaFuncionairios("NELSON");
		embarqueDesembarqueController.listaembarque(null,null,null);

	}


}
