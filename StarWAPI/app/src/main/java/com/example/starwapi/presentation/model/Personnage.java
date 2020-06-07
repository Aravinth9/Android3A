package com.example.starwapi.presentation.model;

import java.util.List;

public class Personnage {


    public String name, height, mass, eye_color, hair_color, skin_color, birth_year, gender, homeworld;

    public List<String> films;

    public List<String> species;

    public List<String> vehicles;

    @Override
    public String toString()
    {
        return "Genre: " + gender + "\n"
                + "Taille: " + height  + " cm" +"\n"
                + "Poids: " + mass +" kg"+ "\n"
                + "Année de naissance: " + birth_year +"\n"
                + "Couleur de cheveux: " + hair_color +"\n"
                + "Couleur de peau: " + skin_color + " \n"
                ;
    }

    public String resume(){
        return "Genre: " + gender + "\n"
                + "Taille: " + height + " cm" + "\n"
                + "Poids: " + mass +" kg"+ "\n";
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
    }

    public String getEye_color() {
        return eye_color;
    }

    public String getHair_color() {
        return hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public String getGender() {
        return gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<String> getFilms() {
        return films;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public List<String> getStarships() {
        return starships;
    }

    public List<String> starships;
}
