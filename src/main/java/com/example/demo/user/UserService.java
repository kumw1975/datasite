package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

  private final TypeReference<List<ProjectUser>> USER_TYPE_REF = new TypeReference<List<ProjectUser>>(){};
  private final TypeReference<List<RegisteredUser>> REGISTERED_USER_TYPE_REF = new TypeReference<List<RegisteredUser>>(){};
  private final TypeReference<List<UnRegisteredUser>> UNREGISTERED_USER_TYPE_REF = new TypeReference<List<UnRegisteredUser>>(){};

  private static final String PROJECT_USERS = "https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships";
  private static final String REGISTERED_USERS = "https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers";
  private static final String UNREGISTERED_USERS = "https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers";

  public List<User> getUsers() {

    List<ProjectUser> projectUsers = (List<ProjectUser>) request(PROJECT_USERS, USER_TYPE_REF);

    List<RegisteredUser> registeredUsers = (List<RegisteredUser>) request(REGISTERED_USERS, REGISTERED_USER_TYPE_REF);

    List<UnRegisteredUser> unRegisteredUsers = (List<UnRegisteredUser>) request(UNREGISTERED_USERS, UNREGISTERED_USER_TYPE_REF);

    List<User> allUsers = Stream.concat(
      registeredUsers.stream(),
      unRegisteredUsers.stream()
    ).collect(Collectors.toList());

    Map<String, User> userMap = allUsers.stream().collect(Collectors.toMap(User::getId,user -> user));

    for (ProjectUser user : projectUsers) {
      if (Objects.nonNull(user.getUserId())){
        userMap.get(user.getUserId()).getProjects().add(user.getProjectId());
      }
    }
    return allUsers;
  }

  private List<?> request(String url, TypeReference<?> ref){
    try {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
        .url(url)
        .get()
        .addHeader("cache-control", "no-cache")
        .build();
      Response response = client.newCall(request).execute();
      String body = response.body().string();
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return (List<?>) mapper.readValue(body, ref);
    }catch (Exception e){
      throw new ValidationException();
    }
  }
}
