package lol.esports.lol_esports_bot;

public class App {


    public static void main(String[] args) {
        Scrapper leagueTable = new Scrapper();

        leagueTable.getLeagueTable("split1", "playoffs", "cblol");
    }
}
