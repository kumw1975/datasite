package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

  private UserService userService;

  @BeforeEach
  public void initializeService() {
    this.userService = new UserService();
  }

  @Test
  public void shouldReturnAllUsers() {
    assertEquals(userService.getUsers().size(), 35);
  }

  @Test
  public void shouldReturnRegisteredUser() {
    assertEquals(userService.getUsers().get(0).getId(), "1");
  }

  @Test
  public void shouldReturnUnRegisteredUser() {
    assertEquals(userService.getUsers().get(34).getId(), "215");
  }

  @Test
  public void shouldReturnUnRegisteredUserWithProjects() {
    assertEquals(userService.getUsers().get(0).getProjects(), Arrays.asList("1", "2"));
  }

  @Test
  public void shouldReturnEmptyProjectCollectionIfUserHasNoProjects() {
    assertEquals(userService.getUsers().get(1).getProjects(), new ArrayList<>());
  }
}

