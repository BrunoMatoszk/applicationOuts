package clube.outs.application.adapter;

import clube.outs.application.dto.PaymentRequest;
import clube.outs.application.dto.PaymentResponse;
import clube.outs.application.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CieloPaymentAdapter implements PaymentService {

    @Value("${cielo.api.url}")
    private String cieloApiUrl;

    @Value("${cielo.merchant.id}")
    private String merchantId;

    @Value("${cielo.merchant.key}")
    private String merchantKey;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            throw new IllegalArgumentException("PaymentRequest cannot be null");
        }

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> paymentData = new HashMap<>();

        // Montar o corpo da requisição com base no tipo de pagamento
        paymentData.put("MerchantOrderId", paymentRequest.getOrderId());
        paymentData.put("Customer", Map.of("Name", paymentRequest.getCustomerName()));

        // Lógica para lidar com diferentes formas de pagamento
        switch (paymentRequest.getPaymentType()) {
            case "CreditCard":
                paymentData.put("Payment", createCreditCardPayment(paymentRequest));
                break;
            case "DebitCard":
                paymentData.put("Payment", createDebitCardPayment(paymentRequest));
                break;
            case "Pix":
                paymentData.put("Payment", createPixPayment(paymentRequest));
                break;
            default:
                throw new IllegalArgumentException("Invalid payment type");
        }

        // Montar cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("MerchantId", merchantId);
        headers.set("MerchantKey", merchantKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(paymentData, headers);

        // Fazer a chamada POST
        ResponseEntity<String> response = restTemplate.postForEntity(cieloApiUrl, entity, String.class);

        // Construir a resposta para ser retornada ao cliente
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(response.getStatusCode().toString());
        paymentResponse.setResponseBody(response.getBody());
        return paymentResponse;
    }

    // Método auxiliar para pagamento com cartão de crédito
    private Map<String, Object> createCreditCardPayment(PaymentRequest paymentRequest) {
        return Map.of(
                "Type", "CreditCard",
                "Amount", paymentRequest.getAmount(),
                "Installments", paymentRequest.getInstallments(),
                "Currency", paymentRequest.getCurrency(),
                "CreditCard", Map.of(
                        "CardNumber", paymentRequest.getCardNumber(),
                        "Holder", paymentRequest.getCardHolder(),
                        "ExpirationDate", paymentRequest.getExpirationDate(),
                        "SecurityCode", paymentRequest.getCvv(),
                        "Brand", paymentRequest.getCardBrand()
                )
        );
    }

    // Método auxiliar para pagamento com cartão de débito
    private Map<String, Object> createDebitCardPayment(PaymentRequest paymentRequest) {
        return Map.of(
                "Type", "DebitCard",
                "Amount", paymentRequest.getAmount(),
                "DebitCard", Map.of(
                        "CardNumber", paymentRequest.getCardNumber(),
                        "Holder", paymentRequest.getCardHolder(),
                        "ExpirationDate", paymentRequest.getExpirationDate(),
                        "SecurityCode", paymentRequest.getCvv(),
                        "Brand", paymentRequest.getCardBrand()
                ),
                "ReturnUrl", "http://localhost:8080/" // URL para onde o usuário será redirecionado após o pagamento
        );
    }


    // Método auxiliar para pagamento via Pix
    private Map<String, Object> createPixPayment(PaymentRequest paymentRequest) {
        return Map.of(
                "Type", "Pix",
                "Amount", paymentRequest.getAmount(),
                "Pix", Map.of(
                        "ExpirationDate", "2024-12-31T23:59:59", // Data de expiração do PIX
                        "QRCode", "Gerar código QR", // Aqui você teria o processo de geração do QR code
                        "PixKey", "chave-pix-exemplo" // Pode ser uma chave PIX gerada
                )
        );
    }

}
