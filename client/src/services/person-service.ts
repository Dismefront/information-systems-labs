import { ApiClient, ApiResponse } from './api-client';

export interface Person {
    id: number;
    name: string;
    eyeColor: string;
    hairColor: string;
    location: {
        id: number;
        x: number;
        y: number;
        z: number;
    };
    height: number | null;
    nationality: string;
}

export interface PersonRequest {
    name: string;
    eyeColor: string;
    hairColor: string;
    locationId: number;
    height?: number;
    nationality: string;
}

export class PersonService {
    static async getList(page: number, size: number): Promise<ApiResponse<Person>> {
        return ApiClient.get<ApiResponse<Person>>(`/api/person/list?page=${page}&size=${size}`);
    }

    static async getAll(): Promise<Person[]> {
        return ApiClient.get<Person[]>('/api/person/get-all');
    }

    static async create(person: PersonRequest): Promise<Person> {
        return ApiClient.post<Person>('/api/person/create', person);
    }

    static async update(id: number, person: PersonRequest): Promise<Person> {
        return ApiClient.put<Person>(`/api/person/update/${id}`, person);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/person/delete/${id}`);
    }
}