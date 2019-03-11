import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { isNull } from 'util';

import { LoginDto } from '../dto/login-dto';
import { LoginResponse } from '../interfaces/login-response';
import { UserService } from './user.service';

const authUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  request(email: String, password: String) {
    let emailPass: String;
    emailPass = btoa(email + ':' + password);
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic ${emailPass}`
      })
    };

    return requestOptions;
  }

  constructor(private http: HttpClient) { }

  login(loginDto: LoginDto): Observable<LoginResponse> {
    const requestOptions = this.request(loginDto.email, loginDto.password);
    return this.http.post<LoginResponse>(`${authUrl}/auth`, environment.masterKey, requestOptions);
  }

  setLoginData(loginResponse: LoginResponse) {
    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('name', loginResponse.user.name);
    localStorage.setItem('email', loginResponse.user.email);
    localStorage.setItem('role', loginResponse.user.role);
    localStorage.setItem('picture', loginResponse.user.picture);
  }

  logout() {
    localStorage.clear();
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  getName(): string {
    return localStorage.getItem('name');
  }

  getEmail(): string {
    return localStorage.getItem('email');
  }

  getPicture(): string {
    return localStorage.getItem('picture');
  }

  isAdmin() {
    return localStorage.getItem('role') === 'admin';
}
}
