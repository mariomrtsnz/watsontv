import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { UserResponse } from '../interfaces/user-response';
import { UserDto } from '../dto/user-dto';
import { ResponseContainer } from '../interfaces/response-container';
import { UserUpdateDto } from '../dto/user-update-dto';

const userUrl = `${environment.apiUrl}/users`;
const masterKey = `?access_token=${environment.masterKeyCreate}`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  token = `?access_token=${this.authService.getToken()}`;
  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll(): Observable<ResponseContainer<UserResponse>> {
    return this.http.get<ResponseContainer<UserResponse>>(`${userUrl}${this.token}`);
  }

  getOne(id: string): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${userUrl}/${id}${this.token}`);
  }

  getMe(): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${userUrl}/me${this.token}`);
  }

  getAllUsers(): Observable<any[]> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        'Access-Control-Allow-Credentials': 'true'
      })
    };

    return this.http.get<any[]>(`${userUrl}/all`, requestOptions);
  }

  remove(id: string): Observable<UserResponse[]> {
    return this.http.delete<UserResponse[]>(`${userUrl}/${id}${this.token}`);
  }

  create(resource: UserDto): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${userUrl}${masterKey}`, resource);
  }

  edit(id: string, resource: UserUpdateDto): Observable<UserResponse> {
    return this.http.put<UserResponse>(`${userUrl}/${id}${this.token}`, resource);
  }

  editMyProfile(user: UserResponse, id: string): Observable<UserResponse> {
    return this.http.put<UserResponse>(`${userUrl}/${id}${this.token}`, user);
  }

  getOneByEmail(email: string): any {
    let foundUser;
    this.getAllUsers().subscribe(
      users => {
        foundUser = users.find(user => {
          return user.email.toLowerCase() === email.toLowerCase();
        });
      }
    );
    return foundUser;
  }
}
