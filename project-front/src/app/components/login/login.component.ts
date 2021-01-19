import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Login } from '../../model/login.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    "username": new FormControl('', [Validators.required]),
    "password": new FormControl('', Validators.required)
  });

  constructor(private service: UserService,
              private router: Router) {}

  loginUser(): void {
    const loginDto = new Login(this.loginForm.value.username, this.loginForm.value.password);
    this.service.login(loginDto).subscribe(
      (response => {
        console.log(response);
        if (response != null) {
          //console.log(localStorage.getItem('token'));
          localStorage.setItem('token', response.token);
          const jwt: JwtHelperService = new JwtHelperService();
          const info = jwt.decodeToken(response.token);
          //console.log(info);
          const role = info.role[0].authority;
          localStorage.setItem('role', info.role[0].authority);
          localStorage.setItem('username', info.sub);
          //console.log('Logged In successfully.');
          //console.log(localStorage.getItem('token'));
          this.router.navigateByUrl('/home');
        }
      }),
      (error => {
        console.log(error.error.message);
      }));
  }
  
  ngOnInit(): void {
  }

}
