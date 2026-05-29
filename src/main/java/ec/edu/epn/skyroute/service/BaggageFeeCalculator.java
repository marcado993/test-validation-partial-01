package ec.edu.epn.skyroute.service;

import org.springframework.stereotype.Service;

/**
 * Calcula las tarifas de equipaje para la aerolínea SkyRoute Airlines.
 * <p>
 * Reglas de negocio:
 * <ol>
 *   <li>Tarifa base: $30.0 por maleta.</li>
 *   <li>Exceso de peso: +$50.0 si una maleta pesa más de 23 kg.</li>
 *   <li>Beneficio VIP: primera maleta gratis si el pasajero es VIP
 *       y la maleta no excede 23 kg.</li>
 *   <li>Excepciones: weight ≤ 0, bagCount < 1, o passengerId nulo
 *       lanzan IllegalArgumentException.</li>
 * </ol>
 */
@Service
public class BaggageFeeCalculator {

    //primeramente prodeceremos a implementar la lógica de negocio propiamente dicha de esta 
    //clase.

    private final PassengerService passengerService;

    public BaggageFeeCalculator(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Calcula la tarifa total de equipaje.
     *
     * @param weight       peso de cada maleta (kg)
     * @param bagCount     cantidad de maletas
     * @param passengerId  identificador del pasajero
     * @return costo total en dólares
     * @throws IllegalArgumentException si los parámetros no cumplen las restricciones
     */
    public double calculateFee(double weight, int bagCount, Long passengerId) {
        // TODO: Implementar lógica de negocio y validación de excepciones
        //
        //primero vamos con las validaciones, peso no debe ser menor que 0, el bagcount no debe ser menor que 1
        //y el passanger id no debe ser nulo

        if(weight <=0||bagCount <1|| passengerId == null){
            throw new illegalArgumentException("Parametros de equipaje inválidos");
        }
        //vamos a delcarar ahora unas variables dado que ya se pasaron las validaciones
        double baseFee = 30.0;
        double overheightFee = 50.0;
        boolean isVip = passengerService.isVip(passengerId);

        double total = 0.0;
        for (int i = 0; i < bagCount; i++) {
            if (isVip && i == 0 && weight <= 23) {
                continue; // De la lógica tenemos que, la primera maleta gratis para VIP
            }
            total += baseFee;
            if (weight > 23) {
                total += overheightFee;
            }
        }



        return total;
    }
}
