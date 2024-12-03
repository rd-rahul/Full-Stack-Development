import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DropdownService {
  private dropdownApiUrl = 'http://localhost:8080/api/dropdown-data';
  private filteredDropdownApiUrl = 'http://localhost:8080/api/filtered-dropdown-data';

  constructor(private http: HttpClient) {}

  // Fetch all dropdown data
  getDropdownData(): Observable<{
    brands: string[],
    types: string[],
    colors: string[],
    transmissions: string[]
  }> {
    console.log('Fetching initial dropdown data...');
    return this.http.get<{
      brands: string[],
      types: string[],
      colors: string[],
      transmissions: string[]
    }>(this.dropdownApiUrl);
  }

  // Fetch filtered dropdown data
  getFilteredDropdownData(filters: { brand?: string; type?: string; color?: string }): Observable<{
    types: string[],
    colors: string[],
    transmissions: string[]
  }> {
    let params = new HttpParams();
    if (filters.brand) {
      params = params.set('brand', filters.brand);
    }
    if (filters.type) {
      params = params.set('type', filters.type);
    }
    if (filters.color) {
      params = params.set('color', filters.color);
    }

    console.log('Fetching filtered dropdown data with filters:', filters);
    return this.http.get<{
      types: string[],
      colors: string[],
      transmissions: string[]
    }>(this.filteredDropdownApiUrl, { params });
  }
}
