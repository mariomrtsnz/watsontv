import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';

const userUrl = `${environment.apiUrl}/users`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }
}
