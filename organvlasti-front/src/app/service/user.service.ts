import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from '../model/login.model';
import { Jwt } from '../model/jwt.model';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  
  private path = 'http://localhost:8080';

  signUp(user: User): Observable<User> {
    return this.http.post<User>(this.path + '/api/user/register', user);
  }

  login(dto: Login): Observable<Jwt> {
    return this.http.post<Jwt>(this.path + '/api/user/login', dto);
  }
}
