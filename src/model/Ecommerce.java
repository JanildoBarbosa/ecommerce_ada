package model;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ecommerce implements GerenciarCliente, GerenciarProduto, GerenciarPedido{

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Pedido pedidoAtual = null;
    private static Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. 👥 Cadastrar Cliente");
            System.out.println("2. 📦 Cadastrar Produto");
            System.out.println("3. 🛒 Criar Pedido");
            System.out.println("4. ➕ Adicionar Item ao Pedido");
            System.out.println("5. ✅ Finalizar Pedido");
            System.out.println("6. \uD83D\uDCB0 Realizar Pagamento");
            System.out.println("7. \uD83D\uDE9A Entregar Pedido");
            System.out.println("8. 📋 Listar Clientes");
            System.out.println("9. 📋 Listar Produtos");
            System.out.println("10. 📋 Listar Pedidos");
            System.out.println("0. ❌ Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarProduto();
                case 3 -> criarPedido();
                case 4 -> adicionarItemPedido();
                case 5 -> finalizarPedido();
                case 6 -> realizarPagamento();
                case 7 -> entregarPedido();
                case 8 -> listarClientes();
                case 9 -> listarProdutos();
                case 10 -> listarPedidos();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    @Override
    public void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Documento: ");
        int documento = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = new Cliente(nome, documento);
        clientes.add(cliente);
        System.out.println("✅ Cliente cadastrado!");
    }

    @Override
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("📭 Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n=== CLIENTES ===");
        for (Cliente cliente : clientes) {
            cliente.mostrarInformacoes();
        }
    }

    @Override
    public void alterarCliente() {

    }

    @Override
    public void criarPedido() {
        if (clientes.isEmpty()) {
            System.out.println("❌ Nenhum cliente cadastrado!");
            return;
        }

        System.out.print("Documento do cliente: ");
        int documento = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = buscarClientePorDocumento(documento);
        if (cliente != null) {
            pedidoAtual = new Pedido(cliente);
            pedidos.add(pedidoAtual);
            System.out.println("✅ Pedido criado para: " + cliente.getNome() + " | " + pedidoAtual.getDataCriacao());
        } else {
            System.out.println("❌ Cliente não encontrado!");
        }
    }

    @Override
    public void adicionarItemPedido() {
        if (pedidoAtual == null) {
            System.out.println("❌ Crie um pedido primeiro!");
            return;
        }

        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        Produto produto = buscarProdutoPorNome(nome);
        if (produto != null) {
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();

            System.out.print("Preço de venda: ");
            double precoVenda = scanner.nextDouble();
            scanner.nextLine();

            ItemPedido item = new ItemPedido(produto, quantidade, precoVenda);
            pedidoAtual.adicionarItem(item);
            System.out.println("✅ Item adicionado!");
        } else {
            System.out.println("❌ Produto não encontrado!");
        }
    }

    @Override
    public void finalizarPedido() {
        if (pedidoAtual == null) {
            System.out.println("❌ Nenhum pedido em andamento!");
            return;
        }

        if (pedidoAtual.podeSerFinalizado()) {
            pedidoAtual.setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
            System.out.println("✅ Pedido finalizado! Status: " + pedidoAtual.getStatus());

            // Notificação simples
            System.out.println("📧 Email enviado para: " + pedidoAtual.getCliente().getNome());
            System.out.println("💬 Mensagem: Pedido aguardando pagamento de R$ " + pedidoAtual.getValorTotal());
            pedidoAtual.setStatusPedido(StatusPedido.FECHADO);
        } else {
            System.out.println("❌ Pedido não pode ser finalizado! Adicione itens primeiro.");
        }
    }

    public void realizarPagamento(){
        if (pedidoAtual == null){
            System.out.println("❌ Realize o pedido primeiro!");
            return;
        }

        if (pedidoAtual.getStatusPagamento() != StatusPagamento.AGUARDANDO_PAGAMENTO){
            System.out.println("❌ Pedido aguardando pagamento de R$ " + pedidoAtual.getValorTotal());
            return;
        }

        System.out.println("✅ Pagamento realizado com Sucesso!");
        pedidoAtual.setStatus(StatusPagamento.PAGO);
    }

    public void entregarPedido(){
        if (pedidoAtual == null){
            System.out.println("❌ Realize o pedido primeiro!");
            return;
        }

        if (pedidoAtual.getStatusPagamento() != StatusPagamento.PAGO){
            System.out.println("❌ Finalize o pedido primeiro!");
            return;
        }

        System.out.println("✅ Entrega realizada com Sucesso!");

    }

    @Override
    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("📭 Nenhum pedido realizado.");
            return;
        }

        System.out.println("\n=== PEDIDOS ===");
        for (Pedido pedido : pedidos) {
            pedido.mostrarInformacoes();
            System.out.println("---------------");
        }
    }

    @Override
    public void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        Produto produto = new Produto(nome, quantidade, preco);
        produtos.add(produto);
        System.out.println("✅ Produto cadastrado!");
    }

    @Override
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("📭 Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n=== PRODUTOS ===");
        for (Produto produto : produtos) {
            produto.mostrarInformacoes();
        }
    }

    private static Cliente buscarClientePorDocumento(int documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumentoIdentificacao() == documento) {
                return cliente;
            }
        }
        return null;
    }

    private static Produto buscarProdutoPorNome(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null;
    }
}
