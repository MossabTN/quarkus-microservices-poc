import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';

import {Users} from '../model/users.model';


@Injectable()
export class UsersService {
  constructor(private http: HttpClient) {}

  list(): Observable<Users[]> {
    return this.http.get<Users[]>("");
  }
}
