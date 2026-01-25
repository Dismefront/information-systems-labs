import { ApiClient, ApiResponse } from './api-client';

export interface Organization {
    id: number;
    name: string;
    officialAddress: {
        id: number;
        zipCode: string;
    };
    annualTurnover: number;
    employeesCount: number | null;
    fullName: string | null;
    postalAddress: {
        id: number;
        zipCode: string;
    };
}

export interface OrganizationRequest {
    name: string;
    officialAddressId: number;
    annualTurnover: number;
    employeesCount?: number;
    fullName?: string;
    postalAddressId: number;
}

export class OrganizationService {
    static async getList(page: number, size: number): Promise<ApiResponse<Organization>> {
        return ApiClient.get<ApiResponse<Organization>>(`/api/organization/list?page=${page}&size=${size}`);
    }

    static async getAll(): Promise<Organization[]> {
        return ApiClient.get<Organization[]>('/api/organization/get-all');
    }

    static async create(organization: OrganizationRequest): Promise<Organization> {
        return ApiClient.post<Organization>('/api/organization/create', organization);
    }

    static async update(id: number, organization: OrganizationRequest): Promise<Organization> {
        return ApiClient.put<Organization>(`/api/organization/update/${id}`, organization);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/organization/delete/${id}`);
    }
}