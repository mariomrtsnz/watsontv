import { CollectionDto } from './../dto/collection-dto';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseContainer } from '../interfaces/response-container';
import { OneCollectionResponse } from '../interfaces/one-collection-response';

const genreUrl = `${environment.apiUrl}/collections`;

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  token = `?access_token=${this.authService.getToken()}`;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneCollectionResponse>>(`${genreUrl}${this.token}`);
  }

  getOne(id: string) {
    return this.http.get<OneCollectionResponse>(`${genreUrl}/${id}${this.token}`);
  }

  create(resource: CollectionDto): Observable<OneCollectionResponse> {
    return this.http.post<OneCollectionResponse>(`${genreUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneCollectionResponse>> {
    return this.http.delete<ResponseContainer<OneCollectionResponse>>(`${genreUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: CollectionDto): Observable<OneCollectionResponse> {
    return this.http.put<OneCollectionResponse>(`${genreUrl}/${id}${this.token}`, resource);
  }
}
