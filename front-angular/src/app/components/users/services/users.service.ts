import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Users} from '../model/users.model';
import {UserConfig} from "../users.config";
import {PaginationArgs, PaginationPage, PaginationSortArgs} from "../../../shared/pagination.args";

@Injectable()
export class UsersService {
  constructor(private http: HttpClient) {}

  getAll(paginationArgs: PaginationArgs): Observable<PaginationPage<Users>> {
    let params = new HttpParams();
    params = params.set('size', `${paginationArgs.pageSize}`);
    params = params.set('page', `${paginationArgs.pageNumber}`);
    if (paginationArgs.sorts !== null && paginationArgs.sorts) {
      if (paginationArgs.sorts.length > 0) {
        paginationArgs.sorts.forEach((sort: PaginationSortArgs) =>
            params = params.set('sort', `${sort.property},${sort.direction}`)
        );
      }
    }
    return this.http.get<PaginationPage<Users>>(UserConfig.api,{params});
  }

  getById(id): Observable<Users> {
    return this.http.get<Users>(UserConfig.api+id)
  }

  update(user: Users): Observable<Users> {
    return this.http.put(UserConfig.api, user);
  }

  delete(id): Observable<any> {
    return this.http.delete(UserConfig.api+id)
  }
}
