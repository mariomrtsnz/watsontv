import { UserService } from './../../services/user.service';
import { CollectionDto } from './../../dto/collection-dto';
import { CollectionService } from './../../services/collection.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { UserResponse } from 'src/app/interfaces/user-response';

@Component({
  selector: 'app-dialog-collection',
  templateUrl: './dialog-collection.component.html',
  styleUrls: ['./dialog-collection.component.scss']
})
export class DialogCollectionComponent implements OnInit {
  edit: boolean;
  name: string;
  description: string;
  collectionId: string;
  ownerId: string;
  form: FormGroup;
  allUsers: UserResponse[];

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private collectionService: CollectionService, public dialogRef: MatDialogRef<DialogCollectionComponent>, private userService: UserService) { }

  ngOnInit() {
    this.getAllUsers();
    this.createForm();
    if (this.data) {
      this.edit = true;
      this.collectionId = this.data.collection.id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedCollection: CollectionDto = <CollectionDto>this.form.value;
      this.collectionService.edit(this.collectionId, editedCollection).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this collection.', 'Close', { duration: 3000 }));
    } else {
      const newCollection: CollectionDto = <CollectionDto>this.form.value;
      this.collectionService.create(newCollection).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this collection.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.collection.name, Validators.compose([Validators.required])],
        description: [this.data.collection.description, Validators.compose([Validators.required])],
        owner: [this.data.collection.owner._id, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])],
        description: [null, Validators.compose([Validators.required])],
        owner: [null, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

  getAllUsers() {
    this.userService.getAll().subscribe(
      users => {
        this.allUsers = users.rows;
      }, error => this.snackBar.open('There was an error when were loading data.', 'Close', { duration: 3000 }));
  }


  addCollection() {
    const collectionCreateDto = new CollectionDto(this.name, this.description, this.ownerId);
    this.collectionService.create(collectionCreateDto).subscribe(
      collection => {
        this.dialogRef.close('confirm');
      }
    );
  }

  editCollection() {
    const collectionEditDto = new CollectionDto(this.name, this.description, this.ownerId);
    this.collectionService.edit(this.collectionId, collectionEditDto).subscribe(
      collection => {
        this.dialogRef.close('confirm');
      }
    );
  }

}
