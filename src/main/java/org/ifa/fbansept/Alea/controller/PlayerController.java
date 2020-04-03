package org.ifa.fbansept.Alea.controller;

import org.ifa.fbansept.Alea.DAO.DAOgame;
import org.ifa.fbansept.Alea.DAO.DAOplayer;
import org.ifa.fbansept.Alea.model.Game;
import org.ifa.fbansept.Alea.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin //(origins = "http://localhost:4200")
public class PlayerController {
    DAOplayer playerDAO;
    @Autowired
    public PlayerController(final DAOplayer playerDAO) {
        this.playerDAO = playerDAO;
    }

    @GetMapping("/")
    public String homepage(@PathVariable int id){
        return "Alea API. Ping success !";

    }

    @GetMapping("/listPlayer/{id}")
    public Player singlePlayer(@PathVariable int id){
        return playerDAO.findById(id).orElse(null);
    }

    @PutMapping("/updatePlayer")
    public Player updateSinglePlayer(@RequestBody Player playerToUpdate){
        Player playerInDB = playerDAO.findById( playerToUpdate.getId() ).orElse(null);
        if ( playerInDB != null){
            /*
            playerInDB.setAge( playerToUpdate.getAge() );
            playerInDB.setLastname( playerToUpdate.getLastname() );
            playerInDB.setFirstname( playerToUpdate.getFirstname() );
    */
            return playerDAO.save(playerInDB);
        } else {
            return null;
        }
    }

    @PutMapping("/setGameToPlayer")
    public Player setGameToPlayer(@RequestBody Game gameToAdd){

        Player playerInDB = playerDAO.findById( 1 ).orElse(null);
        if ( playerInDB != null) {

            Set gameList = playerInDB.getGames();
            gameList.add(gameToAdd);
            playerInDB.setGames(gameList);

            for(Game game : playerInDB.getGames()){
                game.setPlayers(new HashSet<>());
            }

            return playerDAO.save(playerInDB);
        } else {
            return null;
        }


    }


    @GetMapping({"/listPlayer"})
    public List<Player> listPlayer(){
        List<Player> listPlayer = playerDAO.findAll();

        for(Player player : listPlayer){
            for(Game game : player.getGames()){
                game.setPlayers(new HashSet<>());
            }
        }

        return listPlayer;
    }


    @PostMapping({"/addPlayer"})
    Player addPlayer(@RequestBody Player player) {
        return playerDAO.save(player);
    }
}
