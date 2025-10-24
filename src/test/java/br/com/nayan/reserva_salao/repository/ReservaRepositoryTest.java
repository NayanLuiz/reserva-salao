package br.com.nayan.reserva_salao.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReservaRepositoryTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private CasaRepository casaRepository;
    @Mock
    private SalaoRepository salaoRepository;
    @Mock
    private CondominioRepository condominioRepository;

    @Test
    void findByData() {

    }

    @Test
    void findByCondominio() {
    }

    @Test
    void findBySalao() {
    }

    @Test
    void findByCasa() {
    }
}