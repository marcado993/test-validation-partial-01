package ec.edu.epn.skyroute.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class BaggageFeeCalculatorTest {

    //primero ponemos, como dice la lógica de negocio, el mock, el inject mocks 

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private BaggageFeeCalculator calculator;

    private Long regularPassengerId;
    private Long vipPassengerId;

    @BeforeEach
    void setUp() {
        regularPassengerId = 55L;
        vipPassengerId = 10L;
    }

    @Test 
    @DisplayName("Equipaje estándar, 1 maleta, 20 kg, pasajero regular, tarifa $30, caso de prueba 1")
        void CalculateStandardFeeWhenRegularPassenger() {
        // Arrange
        when(passengerService.isVip(regularPassengerId)).thenReturn(false);

        // Act
        double fee = calculator.calculateFee(20.0, 1, regularPassengerId);

        // Assert
        assertEquals(30.0, fee, 0.0001);
    }
    //este caso nos siver para ver un peso >25 nos da un valor de 80
    @Test
    @DisplayName("Exceso de peso, 1 maleta, 25 kg, pasajero regular, $80.00")
    void CalculateOverweightFeeWhenRegularPassenger() {
        // Arrange
        when(passengerService.isVip(regularPassengerId)).thenReturn(false);

        // Act
        double fee = calculator.calculateFee(25.0, 1, regularPassengerId);

        // Assert
        assertEquals(80.0, fee, 0.0001);
    }

}
