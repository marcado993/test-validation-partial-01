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
        assertEquals(30.0, fee);
    }
    //este caso nos sirve para ver un peso >25 nos da un valor de 80
    @Test
    @DisplayName("Exceso de peso, 1 maleta, 25 kg, pasajero regular, $80.00")
    void CalculateOverweightFeeWhenRegularPassenger() {
        // Arrange
        when(passengerService.isVip(regularPassengerId)).thenReturn(false);

        // Act
        double fee = calculator.calculateFee(25.0, 1, regularPassengerId);

        // Assert
        assertEquals(80.0, fee);
    }
    //del terecer caso beneficio vip, vamos a utilizar mockito para poder simular passangerService
    @Test
    @DisplayName("\tBeneficio VIP, 1 maleta, 15 kg, pasajero VIP $0.00 (requiere Mockito)")
    void ChargeZeroWhenVipWithOneBagUnderOrEqual23() {
        // Arrange
        when(passengerService.isVip(vipPassengerId)).thenReturn(true);

        // Act
        double fee = calculator.calculateFee(15.0, 1, vipPassengerId);

        // Assert
        assertEquals(0.0, fee);
    }

    @Test
    @DisplayName("Caso límite VIP,2 maletas, 15 kg c/u, pasajero VIP,$30.00 (1ra gratis, 2da cobro normal)")
    void ChargeNormalFeeWhenVipHaveTwoBags() {
        // Arrange
        when(passengerService.isVip(vipPassengerId)).thenReturn(true);

        // Act
        double fee = calculator.calculateFee(15.0, 2, vipPassengerId);

        // Assert
        assertEquals(30.0, fee);
    }

    @Test
    @DisplayName("Lanza excepcion cuando el peso es invalido")
    void shouldThrowExceptionWhenWeightIsZeroOrNegative() {
        // Arrange

        // Act
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateFee(-1.0, 1, regularPassengerId));

        // Assert
        assertEquals("Parámetros de equipaje inválidos", ex.getMessage());
    }

}
