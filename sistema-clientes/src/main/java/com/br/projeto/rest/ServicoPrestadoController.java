package com.br.projeto.rest;

import com.br.projeto.entity.Cliente;
import com.br.projeto.entity.ServicoPrestado;
import com.br.projeto.repository.ClienteRepository;
import com.br.projeto.repository.ServicoPrestadoRepository;
import com.br.projeto.rest.dto.ServicoPrestadoDTO;
import com.br.projeto.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/servicos-prestados")

@RequiredArgsConstructor
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;

    private final ServicoPrestadoRepository servicoPrestadoRepository;

    private final BigDecimalConverter bigDecimalConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody  @Valid ServicoPrestadoDTO dto){

        LocalDate  data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

           Cliente cliente = clienteRepository.findById(Integer.parseInt(dto.getIdCliente()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cliente Inexisteste"));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescrição(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));

        return servicoPrestadoRepository.save(servicoPrestado);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
                                           @RequestParam(value = "mes", required = false) Integer mes){

        return servicoPrestadoRepository.findByNomeClienteAnMes("%"+nome+"%",mes);
    }
}
