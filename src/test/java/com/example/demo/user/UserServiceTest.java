package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
  Test Cases
  implement a new service with a single endpoint [get-users]
  return all the users [registered and unregistered]
  include the project ids the users belong to.
  If users do not belong to a project, project ids attribute would contain an empty array
  in the response payload.
*/

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
  public void shouldReturnRegisteredUsers() {
    assertEquals(userService.getUsers().get(0).getId(), "1");
  }

  @Test
  public void shouldReturnUnRegisteredUsers() {
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

