import {Component, OnInit} from '@angular/core';
import {UsersService} from "../services/users.service";
import {Users} from "../model/users.model";
import {PaginationArgs} from "../../../shared/pagination.args";
import {ClrDatagridStateInterface} from "@clr/angular";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-users',
  templateUrl: './users.container.component.html',
  styleUrls: ['./users.container.component.css']
})
export class UsersContainerComponent implements OnInit{
  users :Users[] = [];
  loading = true;
  total = 0;
  public paginationArgs: PaginationArgs = { pageNumber: 0, pageSize: 2, sorts: [] };

  modalOpen = false;

  updateUserFormGroup: FormGroup;

  constructor(private usersService: UsersService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.updateUserFormGroup = this.fb.group({
      id: new FormControl(''),
      username: new FormControl('', Validators.required),
      email: new FormControl('',[Validators.required, Validators.email]),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      phoneNumber: new FormControl(''),
      enabled: new FormControl('', Validators.required)
    })
  }

  refresh(ex: ClrDatagridStateInterface){
    this.paginationArgs.pageNumber = ex.page.current;
    this.paginationArgs.pageSize = ex.page.size;
    this.findAll();
  }

  onEdit(user:Users){
    this.usersService.getById(user.id)
        .subscribe(value => {
          this.updateUserFormGroup.patchValue(value);
          this.modalOpen = true;
        })
  }

  updateUser(){
    this.usersService.update(this.updateUserFormGroup.value)
        .subscribe(value => {
          this.findAll();
          this.modalOpen = false;
        })
  }

  onDelete(user:Users){
    this.loading = true;
    this.usersService.delete(user.id)
        .subscribe(value => {
          this.findAll();
        })
  }

  private findAll() {
    this.loading = true;
    this.usersService.getAll(this.paginationArgs)
        .subscribe(value => {
          this.users = [];
          this.users = value.content;
          this.total = value.totalElements;
          this.loading = false;
        })
  }
}
