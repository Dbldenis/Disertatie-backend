package com.example.dizertatie.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicDto {

    // nume, prenume, specializare, telefon, codparafa, parola, email
    // este verificat, cod verificare, cod verificare generare timp
    private String nume;
    private String prenume;
    private String specializare;
    @Size(max = 10, message = "Telefonul nu poate avea mai mult de 10 cifre")
    private String telefon;
    private Long codParafa;
    @Size(max = 15, message = "Parola nu poate avea mai mult de 15 caractere")
    private String parola;
    private String email;
    private Integer aniExperienta;
    private Boolean isVerified = false;
    private String codVerificare;
    private LocalDateTime codVerificareGenerareTimp;

    public void setEsteVerificat(boolean esteVerificat) {
    }
}
//private List<PacientDto> listaPacienti = new ArrayList<>();