package com.haulmont.creditSystem.models;

import java.util.List;

public class Bank {

    private static List<Client> clients;

    private static List<Credit> credits;

    public Bank(){

    }

    public Bank(List<Client> clients, List<Credit> credits){
        setClients(clients);
        setCredits(credits);
    }

    public List<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> _clients) {
        clients = _clients;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public static void setCredits(List<Credit> _credits) {
        credits = _credits;
    }
}
