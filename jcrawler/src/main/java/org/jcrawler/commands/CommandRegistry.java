package org.jcrawler.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
  private final Map<String, Command> commands= new HashMap<>();

  public void register(String flag, Command command){
    commands.put(flag, command);
  }

  public void dispatch(String flag){
    Command cmd= commands.get(flag);
    if(cmd == null){
      System.err.println("Unknown option: " + flag + ",\n use -h or --help to view usage");
      System.exit(1);
    }
    cmd.execute();
  }
}
