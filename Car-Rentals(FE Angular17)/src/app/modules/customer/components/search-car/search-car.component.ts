import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DropdownService } from '../../services/dropdown.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrls: ['./search-car.component.scss']
})
export class SearchCarComponent {
  cars: any = [];
  isSpinning = false;
  validateForm: FormGroup;

  // Dropdown lists
  listOfBrands: string[] = [];
  listOfType: string[] = [];
  listOfColor: string[] = [];
  listOfTransmission: string[] = [];

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private dropdownService: DropdownService
  ) {
    this.validateForm = this.fb.group({
      brand: [null],
      type: [null],
      color: [null],
      transmission: [null]
    });

    this.loadDropdownData();
  }

  // Load initial dropdown data
  loadDropdownData() {
    this.dropdownService.getDropdownData().subscribe((res) => {
      console.log('Initial dropdown data:', res);
      this.listOfBrands = res.brands || [];
      this.listOfType = res.types || [];
      this.listOfColor = res.colors || [];
      this.listOfTransmission = res.transmissions || [];
    });
  }

  // On brand selection change
  onBrandChange(brand: string) {
    console.log('Brand selected:', brand);
    this.dropdownService.getFilteredDropdownData({ brand }).subscribe((res) => {
      console.log('Filtered data after brand change:', res);
      this.listOfType = res.types || [];
      this.listOfColor = res.colors || [];
      this.listOfTransmission = res.transmissions || [];
      this.validateForm.patchValue({
        type: null,
        color: null,
        transmission: null
      });
    });
  }

  // On type selection change
  onTypeChange(type: string) {
    const selectedBrand = this.validateForm.get('brand')?.value;
    console.log('Type selected:', type);
    this.dropdownService.getFilteredDropdownData({ brand: selectedBrand, type }).subscribe((res) => {
      console.log('Filtered data after type change:', res);
      this.listOfColor = res.colors || [];
      this.listOfTransmission = res.transmissions || [];
      this.validateForm.patchValue({
        color: null,
        transmission: null
      });
    });
  }

  // On color selection change
  onColorChange(color: string) {
    const selectedBrand = this.validateForm.get('brand')?.value;
    const selectedType = this.validateForm.get('type')?.value;
    console.log('Color selected:', color);
    this.dropdownService.getFilteredDropdownData({ brand: selectedBrand, type: selectedType, color }).subscribe((res) => {
      console.log('Filtered data after color change:', res);
      this.listOfTransmission = res.transmissions || [];
      this.validateForm.patchValue({
        transmission: null
      });
    });
  }

  // Search for cars
  searchCar() {
    this.isSpinning = true;
    console.log('Search criteria:', this.validateForm.value);
    this.customerService.searchCar(this.validateForm.value).subscribe(
      (res) => {
        console.log('Search results:', res);
        this.isSpinning = false;
        this.cars = [];
        res.carDtoList.forEach((element: any) => {
          element.processedImg = 'data:image/jpeg;base64,' + element.returnedImage;
          this.cars.push(element);
        });
      },
      (error) => {
        console.error('Error occurred while searching cars:', error);
        this.isSpinning = false;
      }
    );
  }
}
