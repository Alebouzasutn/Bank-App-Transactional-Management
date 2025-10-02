package com.example.bankapp.service;

public interface PaymentGateway {


        /**
         * Procesa un pago con tarjeta.
         * @param cardNumber número de tarjeta
         * @param amount monto a debitar
         * @return true si aprobado, false si rechazado
         */
        boolean processPayment(String cardNumber, double amount);
    }

