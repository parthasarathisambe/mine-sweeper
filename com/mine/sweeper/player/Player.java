package com.mine.sweeper.player;

public class Player {

  private final String name;

  public Player(String name) {
    this.name = name;
  }

  public void printWelcome() {
    System.out.print("Welcome " + name);
  }

}
