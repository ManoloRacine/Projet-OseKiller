import { render, screen } from "@testing-library/react"
import { BrowserRouter } from "react-router-dom"
import SeeInternships from "../../views/SeeInternships"
import React from "react"

const MockSeeInternships = () => {
    return (
        <BrowserRouter>
            <SeeInternships/>
        </BrowserRouter>
    )
}

describe("internships", () => {

    it("test give offer to card", async () => {

        const stubInitialState = [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022"}]

        React.useState = jest.fn().mockReturnValueOnce([stubInitialState, {}]) ;

        render(<SeeInternships/>) ;
        
        const position = screen.getByText(/testPos/i);
        const salary = screen.getByText(/1\$/i);
        const endDate = screen.getByText(/10-12-2022/i);
        const startDate = screen.getByText(/10-11-2022/i);
        expect(position).toBeInTheDocument() ;
    }) ;

    it("test give multiple offers", async () => {

        const stubInitialState = [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022"},
         {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022"}, {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022"}] ;

        React.useState = jest.fn().mockReturnValueOnce([stubInitialState, {}]) ;

        render(<SeeInternships/>) ;
        
        const positions = screen.queryAllByText(/testPos/i);
        const salaries = screen.queryAllByText(/1\$/i);
        const endDates = screen.queryAllByText(/10-12-2022/i);
        const startDates = screen.queryAllByText(/10-11-2022/i);
        expect(positions.length).toBe(3) ;
        expect(salaries.length).toBe(3) ;
        expect(endDates.length).toBe(3) ;
        expect(startDates.length).toBe(3) ;
    }) ;
})