import { render, screen } from "@testing-library/react"
import { BrowserRouter } from "react-router-dom"
import SeeInternships from "../SeeInternships"
import React from "react"
import axios from "../../../api/axios"
import { act } from "react-dom/test-utils"

const MockSeeInternships = () => {
    return (
        <BrowserRouter>
            <SeeInternships/>
        </BrowserRouter>
    )
}

jest.mock("../../../api/axios")

describe("internships", () => {

    

    it("test give offer to card", async () => {

        const response = {data : [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}]} ;

        axios.get.mockImplementation(() => Promise.resolve(response))

        await act(async () => {
            render(<SeeInternships/>) ;
        })
        
        const position = screen.getByText(/testPos/i);
        const salary = screen.getByText(/1\$/i);
        const endDate = screen.getByText(/10-12-2022/i);
        const startDate = screen.getByText(/10-11-2022/i);
        const companyName = screen.getByText(/testCompany/i);
        expect(position).toBeInTheDocument() ;
        expect(salary).toBeInTheDocument() ;
        expect(endDate).toBeInTheDocument() ;
        expect(startDate).toBeInTheDocument() ;
        expect(companyName).toBeInTheDocument() ;
    }) ;

    it("test give multiple offers", async () => {

        const response = {data : [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"},
         {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}, 
         {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}]} ;

        axios.get.mockImplementation(() => Promise.resolve(response))

        await act(async () => {
            render(<SeeInternships/>) ;
        })
        
        const positions = screen.queryAllByText(/testPos/i);
        const salaries = screen.queryAllByText(/1\$/i);
        const endDates = screen.queryAllByText(/10-12-2022/i);
        const startDates = screen.queryAllByText(/10-11-2022/i);
        const companyName = screen.queryAllByText(/testCompany/i) ;
        expect(positions.length).toBe(3) ;
        expect(salaries.length).toBe(3) ;
        expect(endDates.length).toBe(3) ;
        expect(startDates.length).toBe(3) ;
        expect(companyName.length).toBe(3) ;
    }) ;
})