import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  private userLoggedIn: boolean = false;
  private username: string = '';

  constructor(private service: UserService) { }

  ngOnInit(): void {
    let token: string | null = localStorage.getItem("token");

    this.userLoggedIn = token !== null;
  }

  logout(): void {
    this.service.logout();
  }

  getUser(): boolean {
    return this.userLoggedIn;
  }

  isUser() {
    return this.service.isUser();
  }

  isUserAdmin(): boolean {
    return this.service.isAdmin();
  }

  getUsername(): string {
    return this.service.getUsername();
  }
}
