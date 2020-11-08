package classes;

import java.util.Objects;

public class Salle {
    private final String nom;
    private int capacite = -1, presents = 0;

    public Salle(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getPresents() {
        return presents;
    }

    public void incPresent() {
        this.presents++;
    }

    public void decPresent() {
        this.presents--;
    }

    public boolean getSaturee() {
        return capacite > -1 && this.getPresents() > this.getCapacite();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return Objects.equals(nom, salle.nom);
    }
}