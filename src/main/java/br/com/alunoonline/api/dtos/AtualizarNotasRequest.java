package br.com.alunoonline.api.dtos;

import lombok.Data;
@Data
public class AtualizarNotasRequest {
    private Double grade1;
    private Double grade2;
}

//DTO - Data Transfer Object - Transferir os dados com segurança qualidade e código limpo.