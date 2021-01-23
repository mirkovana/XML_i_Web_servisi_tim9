import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from '../model/login.model';
import { Jwt } from '../model/jwt.model';
import { User } from '../model/user.model';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private route: Router) { }
  
  private path = 'http://localhost:8070';

  signUp(user: User): Observable<User> {
    return this.http.post<User>(this.path + '/api/user/register', user);
  }

  login(dto: Login): Observable<Jwt> {
    return this.http.post<Jwt>(this.path + '/api/user/login', dto);
  }

  logout(): void {
    localStorage.removeItem("token");
    this.route.navigate(['/']);
  }

  getToken(): string {
    return <string> localStorage.getItem("token");
  }

  getUsername(): string {
    return <string> localStorage.getItem("username");
  }

  getRole(): string {
    return <string> localStorage.getItem("role");
  }
  
  isAdmin(): boolean {
    let authority = this.getRole();
    let role = "ROLE_POVERENIK";
    return authority === role;
  }

  isUser(): boolean {
    let authority = this.getRole();
    let role = "ROLE_USER";
    return authority === role;
  }

  isLoggedIn(): boolean {
    let token = this.getToken();
    return !!token;
  }

}
