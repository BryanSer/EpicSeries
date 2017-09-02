/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Guy;

/**
 *
 * @author Bryan_lzh
 */
public enum Competence {
    Member,
    Admin,
    Owner;

    public static Competence getCompetence(String s) {
        s = s.toUpperCase();
        switch (s) {
            case "M":
                return Competence.Member;
            case "A":
                return Competence.Admin;
            case "O":
                return Competence.Owner;
        }
        return null;
    }

    public boolean isAdmin() {
        switch (this) {
            case Member:
                return false;
            case Admin:
                return true;
            case Owner:
                return true;
        }
        return false;
    }

    public String getData() {
        switch (this) {
            case Member:
                return "M";
            case Admin:
                return "A";
            case Owner:
                return "O";
        }
        return "M";
    }

    @Override
    public String toString() {
        return this.getData();
    }
}
