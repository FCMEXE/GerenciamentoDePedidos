import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Prova {
    public static void main(String[] args) {
        Fila f = new Fila(9); // Fila com pedidos a serem feitos
        Pilha p = new Pilha(9); // Pilha dos pedidos a serem entregues
        ListaEncadeada l = new ListaEncadeada(); // Lista com todos os pedidos
        int op;
        int codigo = 1;
        int pedidoIncluidoFila = 1;
        Pilha pedidosExcluir = new Pilha(6);


        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    null,
                    "Selecione uma opção:" +
                            "\n1 - Novo pedido" +
                            "\n2 - Cancelar pedido" +
                            "\n3 - Listar todos os pedidos" +
                            "\n4 - Incluir pedidos na fila de preparo(3 pedidos)" +
                            "\n5 - Incluir pedidos na pilha de entrega(3 pedidos)" +
                            "\n6 - Gerar relatório de entrega" +
                            "\n0 - Encerrar programa!"));

            switch (op) {
                case 1:

                    Pedido pedido = new Pedido(); // Cria o pedido

                    pedido.setCodigo(codigo);

                    pedido.setPizza(JOptionPane.showInputDialog(
                            null,
                            "Qual o sabor da pizza?"));

                    pedido.setTam(JOptionPane.showInputDialog(
                            null,
                            "Qual o tamanho da pizza?(P, M ou G)"));

                    pedido.setEndereco(JOptionPane.showInputDialog(
                            null,
                            "Qual o endereço para entrega?"));

                    pedido.setDistancia(Float.parseFloat(JOptionPane.showInputDialog(
                            null,
                            "Qual a distância até o endereço?")));

                    codigo++;

                    l.insereNo_fim(new IntNoSimples(pedido)); // Acrescenta o pedido na lista

                    break;

                case 2: // Exclui o pedido pelo código informado

                    if (l.ContarNos() == 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "A lista já está vazia, não é possível excluir nenhum pedido!");
                    }
                    else {
                        int pedidoExcluir = Integer.parseInt(JOptionPane.showInputDialog(
                                null,
                                "Qual o código do pedido que deseja excluir?")); // Solicita o código ao usuário
                        Fila fAux = f;
                        boolean encontrou = false;

                        while (!fAux.vazia()) {
                            if (pedidoExcluir == fAux.desenfileirar().getCodigo()) { // Verifica na fila se o código está nela
                                JOptionPane.showMessageDialog(
                                        null,
                                        "O pedido do código digitado já está sendo preparado e não pode ser excluído!");
                                encontrou = true;
                            }
                        }

                        if (!encontrou) { // Caso o código não seja encontrado na fila
                            if (l.buscaNo(pedidoExcluir)!= null) { // Verifica se o código existe na lista
                                if (!l.buscaNo(pedidoExcluir).pedido.isEmPreparo()) { // Verifica se o pedido não está em preparo
                                    l.excluiNo(pedidoExcluir);
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "O pedido do código digitado já está em preparo e não pode ser excluído!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Não existe pedido para o código digitado!");
                            }
                        }
                    }
                    break;

                case 3: // Exibe a lista de pedidos

                    if(l.ContarNos() == 0){ // Verifica se a lista contem algum pedido

                        JOptionPane.showMessageDialog(
                            null,
                            "A lista de pedidos esta vazia."); // Retorna uma mensagem

                    }
                    else{ // Exibe a lista caso tenha pedidos

                        l.exibeLista();

                    }

                    break;

                // Inclui pedidos para preparo (coloca os pedidos dentro da fila) | Informa quais pedidos foram inseridos na fila e seu código
                case 4:

                    for (int i = 0; i < 3; i++) { // Cria um loop de até 3 pedidos que serão enfileirados na Fila De Preparo
                         pedido = l.buscaNo(pedidoIncluidoFila).pedido;
                        pedido.setEmPreparo(true); // Setar um atributo 'emPreparo' como true para indicar que o pedido está em preparo
                        f.enfileirar(pedido);
                        pedidoIncluidoFila++;
                    }
                    break;

                // Inclui os pedidos para entrega (coloca os pedidos dentro na pilha) | Remover os pedidos da fila de preparo
                case 5: 

                    Pedido aux = new Pedido(); // Cria um Pedido auxiliar

                    // Cria um loop que irá empilhar 3 pedidos da Fila de Preparo e colocá-los na Pilha de Entrega
                    for (int i = 0; i < 3; i++) { 

                        Pedido valorDesenfileirado = f.desenfileirar();
                        p.empilhar(valorDesenfileirado);

                    }

                    ArrayList<Pedido> array = new ArrayList<Pedido>(); // Cria um ArrayList do tipo Pedido

                    while (p.vazia() == true) { // Verifica se a pilha não está vazia

                        array.add(p.desempilhar()); // Caso não esteja vazia, é adicionado ao Array o pedido desempilhado

                    }

                    for (int i = 0; i < array.size(); i++) { // Criação de um loop que vai percorrer o Array inteiro

                        for (int j = 0; j < array.size() - 1; j++) { 

                            // Nesta verificação, as distâncias dos pedidos serão comparadas.
                            if (array.get(j).getDistancia() < array.get(j + 1).getDistancia()) { 

                                aux = array.get(j);
                                array.add(j, array.get(j + 1));
                                array.add(j + 1, aux);

                            }

                        }

                    }

                    // Após a verificação, as distâncias serão organizadas da menor para a maior, formando assim, a Pilha de Entrega
                    for (int i = 0; i < array.size(); i++) { 

                        p.empilhar(array.get(i));

                    }

                    break;

               

                case 6: // Gera o relatório dos 3 pedidos que serão entregues

                    ListaEncadeada pedidosEntregar = new ListaEncadeada(); // Cria uma fila de pedidos a serem entregues

                    if (p.vazia()) { //Verifica se a pilha de entregas está vazia

                        JOptionPane.showMessageDialog(
                                null,
                                "A pilha de pedidos para entrega está vazia!");

                    } 
                    else {

                        for (int i = 0; i < 3; i++) { // Cria um loop para os 3 pedidos

                            Pedido pedidoDesempilhado = p.desempilhar(); // Desempilha um pedido e o armazena

                            pedidosEntregar.insereNo_fim(new IntNoSimples(pedidoDesempilhado)); // Enfileira o pedido desempilhado na Fila De Entregas
                            pedidosExcluir.empilhar(pedidoDesempilhado); // Empilha o pedido na pilha de exclusão de pedidos

                            if (pedidosExcluir.cheia()) { // Verifica se a pilha de exclusão está cheia

                                while (!pedidosExcluir.vazia()) { // Caso cheia, desempilha os valores da pilha de exclusão e os exclui da Lista de Pedidos

                                    l.excluiNo(pedidosExcluir.desempilhar().getCodigo());

                                }

                            }

                        }

                        pedidosEntregar.exibeLista(); // Exibe os pedidos que serão entregues

                    }

                    break;

                case 0: // Encerra o programa
                    
                    JOptionPane.showMessageDialog(
                        null, 
                        "Encerrando programa.");
                    
                    break;

                default: // Retorno da mensagem caso o usuário digite outro número além dos propostos

                    JOptionPane.showMessageDialog(
                            null,
                            "O valor digitado não é válido, tente novamente.");

                    break;

            }

        } while (op != 0);
    }
}