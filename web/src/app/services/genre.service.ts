import { GenreDto } from './../dto/genre-dto';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { ResponseContainer } from '../interfaces/response-container';
import { OneMediaResponse } from '../interfaces/one-media-response';

const genreUrl = `${environment.apiUrl}/genres`;

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  token = `?access_token=${this.authService.getToken()}`;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneMediaResponse>>(`${genreUrl}${this.token}`);
  }

  getOne(id: number) {
    return this.http.get<OneMediaResponse>(`${genreUrl}/${id}${this.token}`);
  }

  create(resource: GenreDto): Observable<OneMediaResponse> {
    return this.http.post<OneMediaResponse>(`${genreUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneMediaResponse>> {
    return this.http.delete<ResponseContainer<OneMediaResponse>>(`${genreUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: GenreDto): Observable<OneMediaResponse> {
    return this.http.put<OneMediaResponse>(`${genreUrl}/${id}${this.token}`, resource);
}
}
