package org.ifa.fbansept.Alea.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.internal.Cascade;
import org.ifa.fbansept.Alea.jsonview.MyJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "turn")
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({MyJsonView.Card.class, MyJsonView.Turn.class})
    private Integer id;

    //Cards that were linked to that turn, player and game
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonView({MyJsonView.Turn.class})
    @JoinTable(
        name = "Card_turn",
        joinColumns = @JoinColumn(name = "turn_id"),
        inverseJoinColumns = @JoinColumn(name = "card_id"))
    Set<Card> cards;

    //Player that played the turn
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonView({MyJsonView.Card.class, MyJsonView.Turn.class})
    @JoinColumn(name="player_id")
    private Player player;

    //Game where the turn occured
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonView({MyJsonView.Card.class, MyJsonView.Turn.class})
    @JoinColumn(name="game_id")
    private Game game;

    public Turn() {
    }

    public void setSingleCard(Card card){
        this.cards.add(card);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
