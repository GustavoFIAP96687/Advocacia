package br.com.fiap;

import br.com.fiap.domain.entity.Advogado;
import br.com.fiap.domain.entity.Estado;
import br.com.fiap.domain.entity.Processo;
import br.com.fiap.domain.entity.TipoDeAcao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory("oracle");
            manager = factory.createEntityManager();

        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }

    private static void findAll(EntityManager manager) {
        List<Processo> list = manager.createQuery("SELECT p FROM Processo p", Processo.class).getResultList();
        list.forEach(System.out::println);
    }

    private static void findById(EntityManager manager) {
        Long id = Long.valueOf(JOptionPane.showInputDialog("ID do Processo"));

        Processo processo = manager.find(Processo.class, id);
        System.out.println(processo);
    }

    private static Processo saveProcesso(EntityManager manager) {
        TipoDeAcao tp = new TipoDeAcao(null, "DESPEJO");

        Estado uf = new Estado(null, "São Paulo", "SP");

        Advogado advogado = new Advogado(null, "Gustavo Nascimento", "96687", uf);

        Processo p1 = new Processo(null, "6745.11.2023.9100-00", true, advogado, tp);

        manager.getTransaction().begin();
        manager.persist(p1);
        manager.getTransaction().commit();
        return p1;
    }
}
