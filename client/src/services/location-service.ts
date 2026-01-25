import { ApiClient, ApiResponse } from './api-client';

export interface Location {
    id: number;
    x: number;
    y: number;
    z: number;
}

export interface LocationRequest {
    x: number;
    y: number;
    z: number;
}

export class LocationService {
    static async getList(page: number, size: number): Promise<ApiResponse<Location>> {
        return ApiClient.get<ApiResponse<Location>>(`/api/location/list?page=${page}&size=${size}`);
    }

    static async getAll(): Promise<Location[]> {
        return ApiClient.get<Location[]>('/api/location/get-all');
    }

    static async create(location: LocationRequest): Promise<Location> {
        return ApiClient.post<Location>('/api/location/create', location);
    }

    static async update(id: number, location: LocationRequest): Promise<Location> {
        return ApiClient.put<Location>(`/api/location/update/${id}`, location);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/location/delete/${id}`);
    }
}