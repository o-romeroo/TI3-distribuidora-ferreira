package com.distribuidoraferreira.backend.mappers; 

// import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Venda;

// @RunWith(MockitoJUnitRunner.class)
public class VendaMapperTest {

//     @InjectMocks
//     private VendaMapper vendaMapper = Mappers.getMapper(VendaMapper.class);

//     @Mock
//     private MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

//     @Before
//     public void setUp() {
//         // Mock para o método movimentacaoEstoqueRequestToMovimentacaoEstoque
//         when(movimentacaoEstoqueMapper.movimentacaoEstoqueRequestToMovimentacaoEstoque(Mockito.any()))
//             .thenReturn(new MovimentacaoEstoque());
//     }

//     @Test
//     public void testVendaRequestToVenda() {
//         // Criação do objeto VendaRequest para teste
//         VendaRequest vendaRequest = new VendaRequest();
//         vendaRequest.setMetodoPagamento("Cartão");
//         vendaRequest.setStatus(StatusVenda.EM_PROCESSAMENTO);
//         MovimentacaoEstoqueRequest movimentacaoEstoqueRequest = new MovimentacaoEstoqueRequest();
//         vendaRequest.setMovimentacaoEstoqueRequests(Arrays.asList(movimentacaoEstoqueRequest));

//         // Mapeamento
//         Venda venda = vendaMapper.vendaRequestToVenda(vendaRequest);

//         // Verificações
//         assertEquals(vendaRequest.getMetodoPagamento(), venda.getMetodoPagamento());
//         assertEquals(vendaRequest.getStatus(), venda.getStatus());
//         assertEquals(1, venda.getMovimentacoesEstoque().size()); // Verifica se houve mapeamento das movimentações
//     }

//     @Test
//     public void testVendaToVendaResponse() {
//         // Criação do objeto Venda para teste
//         Venda venda = new Venda();
//         venda.setId(1L);
//         venda.setDataHora(new Date());
//         venda.setMetodoPagamento("Cartão");
//         venda.setTotalVenda(100.0);
//         venda.setStatus(StatusVenda.CONCLUIDA);

//         // Mapeamento
//         VendaResponse vendaResponse = vendaMapper.vendaToVendaResponse(venda);

//         // Verificações
//         assertEquals(venda.getId(), vendaResponse.getId());
//         assertEquals(venda.getDataHora(), vendaResponse.getDataHora());
//         assertEquals(venda.getMetodoPagamento(), vendaResponse.getMetodoPagamento());
//         assertEquals(venda.getTotalVenda(), vendaResponse.getTotalVenda());
//         assertEquals(venda.getStatus(), vendaResponse.getStatus());
//     }

//     @Test
//     public void testVendasToVendaResponses() {
//         // Criação de uma lista de Vendas para teste
//         Venda venda1 = new Venda();
//         venda1.setId(1L);
//         venda1.setDataHora(new Date());
//         venda1.setMetodoPagamento("Cartão");
//         venda1.setTotalVenda(100.0);
//         venda1.setStatus(StatusVenda.CONCLUIDA);

//         Venda venda2 = new Venda();
//         venda2.setId(2L);
//         venda2.setDataHora(new Date());
//         venda2.setMetodoPagamento("Dinheiro");
//         venda2.setTotalVenda(150.0);
//         venda2.setStatus(StatusVenda.EM_PROCESSAMENTO);

//         List<Venda> vendas = Arrays.asList(venda1, venda2);

//         // Mapeamento
//         List<VendaResponse> vendaResponses = vendaMapper.vendasToVendaResponses(vendas);

//         // Verificações
//         assertEquals(vendas.size(), vendaResponses.size());
//         assertEquals(venda1.getId(), vendaResponses.get(0).getId());
//         assertEquals(venda2.getId(), vendaResponses.get(1).getId());
//         // Adicione mais verificações conforme necessário
//     }
}
