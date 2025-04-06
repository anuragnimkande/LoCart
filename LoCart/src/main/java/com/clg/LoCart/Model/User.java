package com.clg.LoCart.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "users")
public class User {

  @Id
  private String id;
  private String Username;
  private String password;
  private String mobile;
  private String email;


}
