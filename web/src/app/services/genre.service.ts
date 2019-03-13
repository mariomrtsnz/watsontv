import { GenreDto } from './../dto/genre-dto';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { ResponseContainer } from '../interfaces/response-container';
import { OneMediaResponse } from '../interfaces/one-media-response';
import { OneGenreResponse } from '../interfaces/one-genre-response';

const genreUrl = `${environment.apiUrl}/genres`;

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  token = `?access_token=${this.authService.getToken()}`;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneGenreResponse>>(`${genreUrl}${this.token}`);
  }

  getOne(id: string) {
    return this.http.get<OneGenreResponse>(`${genreUrl}/${id}${this.token}`);
  }

  create(resource: GenreDto): Observable<OneGenreResponse> {
    return this.http.post<OneGenreResponse>(`${genreUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneGenreResponse>> {
    return this.http.delete<ResponseContainer<OneGenreResponse>>(`${genreUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: GenreDto): Observable<OneGenreResponse> {
    return this.http.put<OneGenreResponse>(`${genreUrl}/${id}${this.token}`, resource);
  }
}
