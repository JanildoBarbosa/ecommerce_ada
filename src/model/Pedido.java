package model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private LocalDate dataCriacao;
    private StatusPagamento statusPagamento;
    private StatusPedido statusPedido;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private double valorTotal;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.statusPedido = StatusPedido.ABERTO;
        this.statusPagamento = StatusPagamento.INEXISTENTE;
        this.dataCriacao = dataCriacao.now();
        this.valorTotal = 0.0;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public StatusPedido getStatus() {
        return statusPedido;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setStatus(StatusPagamento status) {
        this.statusPagamento = status;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        calcularValorTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }
    }

    public boolean podeSerFinalizado() {
        if (!itens.isEmpty() && valorTotal > 0){
            return true;
        }
        return false;
    }

    public void mostrarInformacoes() {
        System.out.println("=== PEDIDO ===");
        System.out.println("Data: " + dataCriacao.toString());
        System.out.println("Status: " + statusPagamento);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Valor Total: R$ " + valorTotal);

        System.out.println("Itens:");
        for (int i = 0; i < itens.size(); i++) {
            ItemPedido item = itens.get(i);
            System.out.println("  " + (i + 1) + ". " + item.getProduto().getNome() +
                    " - Qtd: " + item.getQuantidade() +
                    " - Preço: R$ " + item.getPrecoVenda());
        }
    }
}