import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {UsersService} from "../services/users.service";
import {Users} from "../model/users.model";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit{
  users$: Observable<Users[]>;

  constructor(private usersService: UsersService) {}

  ngOnInit(): void {
    this.users$ = this.usersService.list();
  }
}
